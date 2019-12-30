XMS = 2g
XMX = 6g

NATIVE_IMAGE_CONFIG_OUTPUT_DIR=native-config

TARGET_JAR=target/substrate-sample-0.1.0-SNAPSHOT-standalone.jar

.PHONY: all
all: server

.PHONY: clean
clean:
	rm -f server
	rm -rf target

.PHONY: install/native-image
install/native-image:
	gu install native-image

.PHONY: profile/native-image-config
profile/native-image-config: \
	$(NATIVE_IMAGE_CONFIG_OUTPUT_DIR) \
	$(TARGET_JAR)
	java -agentlib:native-image-agent=config-output-dir=$(NATIVE_IMAGE_CONFIG_OUTPUT_DIR) \
	    -jar $(TARGET_JAR)

$(NATIVE_IMAGE_CONFIG_OUTPUT_DIR):
	mkdir -p $@

server: \
	$(TARGET_JAR)
	native-image \
	-jar $(TARGET_JAR) \
	-H:Name=server \
	-H:+ReportExceptionStackTraces \
	-J-Dclojure.spec.skip-macros=true \
	-J-Dclojure.compiler.direct-linking=true \
	-H:Log=registerResource: \
	-H:ConfigurationFileDirectories=$(NATIVE_IMAGE_CONFIG_OUTPUT_DIR) \
	--enable-url-protocols=http,https \
	--enable-all-security-services \
	-H:+JNI \
	--verbose \
	--no-fallback \
	--no-server \
	--report-unsupported-elements-at-runtime \
	--initialize-at-build-time \
	--allow-incomplete-classpath \
	-J-Xms$(XMS) \
	-J-Xmx$(XMX)

$(TARGET_JAR): src
	lein uberjar
