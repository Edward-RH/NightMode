#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a symlink
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls -l "$PRG"
    link=`expr "$PRG" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD="maximum"

warn () {
    echo "$*"
}

die () {
    echo
    echo "$*"
    echo
    exit 1
}

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "`uname`" in
  CYGWIN* )
    cygwin=true
    ;;
  Darwin* )
    darwin=true
    ;;
  MSYS* | MINGW* )
    msys=true
    ;;
  NONSTOP* )
    nonstop=true
    ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/bin/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the JDK files -- check for a flag.
        # It looks like $JAVA_HOME/bin/java executes the $JAVA_HOME/bin/java.exe file
        # has a wrapper script around the real executable that proper execs that script
        # to make java exit with the properly adjusted exit status and -D/X options.
        eval '[ "$something" = "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ] && JAVA_HOME="$JAVA_HOME"'
        JAVA_HOME_BIN="$JAVA_HOME/bin"
        PATH="$JAVA_HOME/bin:$PATH"
        export PATH JAVA_HOME
        export JAVA_HOME_BIN
    else
        if [ -x "$JAVA_HOME/bin/java" ] ; then
            # IBM's JDK on AIX uses strange locations for the JDK files -- check for a flag.
            # It looks like $JAVA_HOME/bin/java executes the $JAVA_HOME/bin/java.exe file
            # has a wrapper script around the real executable that proper execs that script
            # to make java exit with the properly adjusted exit status and -D/X options.
            eval '[ "$something" = "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ] && JAVA_HOME="$JAVA_HOME"'
            JAVA_HOME_BIN="$JAVA_HOME/bin"
            PATH="$JAVA_HOME/bin:$PATH"
            export PATH JAVA_HOME
            export JAVA_HOME_BIN
        fi
    fi
fi

JVM_ARGS=""
# For Cygwin or MSYS, switch paths to Windows format before running java
if $cygwin; then
    [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --windows "$JAVA_HOME"`
    [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
elif $msys; then
    [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --windows "$JAVA_HOME"`
    [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --windows "$CLASSPATH"`
fi

if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/bin/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the JDK files -- check for a flag.
        # It looks like $JAVA_HOME/bin/java executes the $JAVA_HOME/bin/java.exe file
        # has a wrapper script around the real executable that proper execs that script
        # to make java exit with the properly adjusted exit status and -D/X options.
        eval '[ "$something" = "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ] && JAVA_HOME="$JAVA_HOME"'
        JAVA_HOME_BIN="$JAVA_HOME/bin"
        PATH="$JAVA_HOME/bin:$PATH"
        export PATH JAVA_HOME
        export JAVA_HOME_BIN
    else
        if [ -x "$JAVA_HOME/bin/java" ] ; then
            # IBM's JDK on AIX uses strange locations for the JDK files -- check for a flag.
            # It looks like $JAVA_HOME/bin/java executes the $JAVA_HOME/bin/java.exe file
            # has a wrapper script around the real executable that proper execs that script
            # to make java exit with the properly adjusted exit status and -D/X options.
            eval '[ "$something" = "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ] && JAVA_HOME="$JAVA_HOME"'
            JAVA_HOME_BIN="$JAVA_HOME/bin"
            PATH="$JAVA_HOME/bin:$PATH"
            export PATH JAVA_HOME
            export JAVA_HOME_BIN
        fi
    fi
fi

JVM_ARGS=""
# For Cygwin or MSYS, switch paths to Windows format before running java
if $cygwin; then
    [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --windows "$JAVA_HOME"`
    [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
elif $msys; then
    [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --windows "$JAVA_HOME"`
    [ -n "$CLASSPATH" ] && CLASSPATH=`cygpath --windows "$CLASSPATH"`
fi

# Escaping properties to avoid shells to interpret some characters as glob patterns
JAVA_ARGS="$( printf '%s\n' "${DEFAULT_JVM_OPTS}" | xargs )"

# Collect all arguments for the java command, stacking in reverse order:
#   * args from the command line
#   * the main class name
#   * -classpath
#   * -D...sysproperties
#   * --module-path
#   * the argfile pointed to by -argfile

exec "$JAVA_HOME/bin/java" $JAVA_ARGS \
        -classpath "$CLASSPATH" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"
