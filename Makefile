XMS = 2g
XMX = 7g

NATIVE_IMAGE_CONFIG_OUTPUT_DIR=native-config

TARGET_JAR=target/substrate-sample-0.1.0-SNAPSHOT-standalone.jar

.PHONY: all
all: server

.PHONY: clean
clean:
	rm -f server
	rm -rf target

.PHONY: profile/native-image-config
profile/native-image-config: \
	$(NATIVE_IMAGE_CONFIG_OUTPUT_DIR) \
	$(TARGET_JAR)
	java -agentlib:native-image-agent=config-output-dir=$(NATIVE_IMAGE_CONFIG_OUTPUT_DIR) \
	    -jar $(TARGET_JAR)

$(NATIVE_IMAGE_CONFIG_OUTPUT_DIR):
	mkdir -p $@
