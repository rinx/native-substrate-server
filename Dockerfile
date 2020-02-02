FROM alpine:edge AS packer

RUN apk update \
    && apk upgrade \
    && apk --update-cache add --no-cache \
    upx

COPY server /server
RUN upx --lzma --best /server

FROM scratch

LABEL maintainer "rinx <rintaro.okamura@gmail.com>"

COPY --from=packer /server /server
COPY assets/server /etc/server

ENTRYPOINT ["/server"]
CMD ["/etc/server/config.edn"]
