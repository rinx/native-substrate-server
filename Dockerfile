FROM scratch

LABEL maintainer "rinx <rintaro.okamura@gmail.com>"

COPY server /server
COPY assets/server /etc/server

ENTRYPOINT ["/server"]
CMD ["/etc/server/config.edn"]
