FROM clojure:openjdk-8-lein-2.9.5-buster as builder
WORKDIR /stonks
RUN apt-get update && \ 
    apt-get -y install curl gnupg && \
    curl -sL https://deb.nodesource.com/setup_15.x  | bash -  && \
    apt-get -y install nodejs && npm install -g shadow-cljs

COPY project.clj /stonks
RUN lein deps
COPY package.json /stonks
RUN npm install
COPY . /stonks

RUN npx shadow-cljs release app 
RUN mv "$(lein uberjar | sed -n 's/^Created \(.*standalone\.jar\)/\1/p')" /stonks/app-standalone.jar

FROM openjdk:8
COPY --from=builder /stonks/app-standalone.jar .
CMD ["java", "-jar", "app-standalone.jar"]]
