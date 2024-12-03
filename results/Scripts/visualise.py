import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import MinMaxScaler
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import LSTM, Dense, Dropout

# Load the CSV file
data = pd.read_csv('C:\\Users\\milli\\MBA_Project2\\Development\\KafkaProject\\results\\TradesToDate.csv', delimiter=';')

# Convert 'TRADE DATE' to datetime format
data['TRADE DATE'] = pd.to_datetime(data['TRADE DATE'])

# Sort data by date
data.sort_values('TRADE DATE', inplace=True)

# Check for missing values and drop or fill them
data.dropna(inplace=True)

# Select relevant features for LSTM
features = data[['OPEN PRICE', 'LOW PRICE', 'HIGH PRICE', 'TRADE VOLUME']]
target = data['ADJ CLOSE PRICE']

# Normalize the features for better performance
scaler_features = MinMaxScaler(feature_range=(0, 1))
scaled_features = scaler_features.fit_transform(features)

scaler_target = MinMaxScaler(feature_range=(0, 1))
scaled_target = scaler_target.fit_transform(target.values.reshape(-1, 1))

# Convert to DataFrame for easier handling
scaled_features_df = pd.DataFrame(scaled_features, columns=features.columns)
scaled_target_df = pd.DataFrame(scaled_target, columns=['ADJ CLOSE PRICE'], index=data['OPEN PRICE'])

def create_dataset(features, target, time_step=1):
    X, y = [], []
    for i in range(len(features) - time_step - 1):
        X.append(features[i:(i + time_step), :])
        y.append(target[i + time_step])
    return np.array(X), np.array(y)

# Define time step (number of previous days to consider)
time_step = 10
X, y = create_dataset(scaled_features, scaled_target, time_step)

# Reshape input to be [samples, time steps, features]
X = X.reshape(X.shape[0], X.shape[1], X.shape[2])

# Split into training and testing sets (80% train, 20% test)
train_size = int(len(X) * 0.8)
X_train, X_test = X[:train_size], X[train_size:]
y_train, y_test = y[:train_size], y[train_size:]


# Build the LSTM model
model = Sequential()
model.add(LSTM(50, return_sequences=True, input_shape=(X_train.shape[1], X_train.shape[2])))
model.add(Dropout(0.2))
model.add(LSTM(50))
model.add(Dropout(0.2))
model.add(Dense(1))  # Output layer

# Compile the model
model.compile(optimizer='adam', loss='mean_squared_error')

# Train the model
model.fit(X_train, y_train, epochs=50, batch_size=20)

# Make predictions on test set
predictions = model.predict(X_test)

# Inverse transform predictions to original scale
predictions_inverse = scaler_target.inverse_transform(predictions)

# Prepare actual values for comparison
y_test_inverse = scaler_target.inverse_transform(y_test.reshape(-1, 1))

# Plotting the results
plt.figure(figsize=(14, 7))
plt.plot(scaled_target_df.index[train_size + time_step + 1:], y_test_inverse, color='blue', label='Actual Open Price')
plt.plot(scaled_target_df.index[train_size + time_step + 1:], predictions_inverse, color='red', label='Predicted Open Price')
plt.title('Open Price Prediction using LSTM')
plt.xlabel('Open Price')
plt.ylabel('Adjusted Close Price')
plt.legend()
plt.show()