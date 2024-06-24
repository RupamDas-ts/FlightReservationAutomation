# Use an official java-22 image as a parent image
FROM openjdk:22-slim

# Set environment variables
ENV MAVEN_VERSION=3.8.6
ENV MAVEN_HOME=/usr/share/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

# Install necessary tools and Maven
RUN apt-get update && \
    apt-get install -y wget curl gnupg && \
    curl -fsSL https://apt.releases.hashicorp.com/gpg | apt-key add - && \
    wget https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -O /tmp/maven.tar.gz && \
    mkdir -p /usr/share/maven && \
    tar -xzf /tmp/maven.tar.gz -C /usr/share/maven --strip-components=1 && \
    rm -f /tmp/maven.tar.gz && \
    apt-get remove -y wget curl gnupg && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Verify installations
RUN java -version && mvn -version

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