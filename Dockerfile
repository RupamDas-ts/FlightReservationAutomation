# Use an official Maven image as a parent image
FROM maven:3.8.6-openjdk-11-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install necessary packages
RUN apt-get update && apt-get install -y \
    xvfb \
    && rm -rf /var/lib/apt/lists/*

# Make the entrypoint.sh script executable
RUN chmod +x /app/entrypoint.sh

# Build the project and run tests
RUN mvn clean compile

# Expose any necessary ports (adjust as needed)
EXPOSE 3000

# Run entrypoint.sh when the container launches
ENTRYPOINT ["/app/entrypoint.sh"]