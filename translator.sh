#!/bin/sh
#
# ---------------------------------------------------------------------
# Deep Server-Side Translator startup script.
# ---------------------------------------------------------------------
#

"$JAVA_HOME"/bin/java -jar PATH-TO-FILE/elasticfiller-*.jar &
"$JAVA_HOME"/bin/java -jar PATH-TO-FILE/redisfiller-*.jar &
"$JAVA_HOME"/bin/java -jar PATH-TO-FILE/translator-*.jar

