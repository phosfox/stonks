FROM clojure:openjdk-11-lein-2.9.5-buster
COPY . /usr/stonks
WORKDIR /usr/stonks
RUN apt-get update
RUN apt-get -y install curl gnupg
RUN curl -sL https://deb.nodesource.com/setup_15.x  | bash -
RUN apt-get -y install nodejs
RUN npm install
RUN lein uberjar
CMD ["java", "-jar", "/usr/stonks/target/uberjar/stonks-0.1.0-standalone.jar"]
