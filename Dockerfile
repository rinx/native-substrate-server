ARG GRAALVM_VERSION=latest

FROM oracle/graalvm-ce:${GRAALVM_VERSION} AS graalvm

LABEL maintainer "rinx <rintaro.okamura@gmail.com>"

RUN gu install native-image
RUN curl -o /usr/bin/lein https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein \
    && chmod a+x /usr/bin/lein

RUN mkdir -p /substrate
WORKDIR /substrate

COPY project.clj project.clj

RUN lein deps

COPY src src
COPY resources resources

RUN lein uberjar

COPY native-config native-config

COPY Makefile Makefile

RUN make

FROM alpine:edge AS packer

RUN apk update \
    && apk upgrade \
    && apk --update-cache add --no-cache \
    upx

COPY --from=graalvm /substrate/server /server
RUN upx --lzma --best /server

FROM alpine:edge

LABEL maintainer "rinx <rintaro.okamura@gmail.com>"

COPY --from=packer /server /server
COPY assets/server /etc/server

COPY entrypoint.sh /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
