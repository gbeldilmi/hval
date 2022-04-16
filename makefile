#------------------------------------------------------------------------------#
# Project name & files                                                         #
#------------------------------------------------------------------------------#
PROJECT_NAME        := hval
JAR_FILE            := $(PROJECT_NAME).jar
MANIFEST_FILE       := manifest.mf
JAVA_FILES          := $(shell find $(PROJECT_NAME)/ -type f -name '*.java')
CLASS_FILES         := $(JAVA_FILES:%.java=%.class)
LIBRARY_DIR         := lib
CLASSPATH           := -classpath .:$(LIBRARY_DIR)/julya.jar
COMPILER_FLAGS      := -Xlint $(CLASSPATH)
RUN_ARGS						:= 

#------------------------------------------------------------------------------#
# Commands                                                                     #
#------------------------------------------------------------------------------#
.PHONY : all, run, clean
all :
	javac $(COMPILER_FLAGS) $(JAVA_FILES)
	jar cfmv $(JAR_FILE) $(MANIFEST_FILE) $(CLASS_FILES) $(LIBRARY_DIR)/
run : all
	java -jar $(JAR_FILE) $(RUN_ARGS)
clean :
	rm -v $(JAR_FILE) $(CLASS_FILES)
