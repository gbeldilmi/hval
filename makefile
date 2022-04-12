#------------------------------------------------------------------------------#
# Project name & files                                                         #
#------------------------------------------------------------------------------#
PROJECT_NAME        := hval
JAR_FILE            := $(PROJECT_NAME).jar
MANIFEST_FILE       := manifest.mf
JAVA_FILES          := $(shell find $(PROJECT_NAME)/ -type f -name '*.java')
CLASS_FILES         := $(JAVA_FILES:%.java=%.class)
COMPILER_FLAGS      := -Xlint

#------------------------------------------------------------------------------#
# Commands                                                                     #
#------------------------------------------------------------------------------#
.PHONY : all, run, clean
all :
	javac $(COMPILER_FLAGS) $(JAVA_FILES)
	jar cfmv $(JAR_FILE) $(MANIFEST_FILE) $(CLASS_FILES)
run : all
	java -jar $(JAR_FILE)
clean :
	rm -v $(JAR_FILE) $(CLASS_FILES)
