# Simple Makefile to compile A.java, B.java, C.java, with main()
# in C.java

BINDIR=./bin
SRCDIR=./src
DOCDIR=./javadocs

# General build rule: .java => .class
# This allows dependencies in different directories
# -cp => specified CLASSPATH
# -d  => where to write .class files

${BINDIR}/%.class: ${SRCDIR}/%.java
	javac $< -cp ${BINDIR} -d ${BINDIR} 

# first build rule
${BINDIR}/WordApp.class: ${BINDIR}/Score.class  ${BINDIR}/WordDictionary.class ${BINDIR}/WordRecord.class ${BINDIR}/WordPanel.class ${BINDIR}/DroppedThread.class ${BINDIR}/GameThread.class ${SRCDIR}/WordApp.java

run:
	java -cp bin/ UnthreadedSimulation 0 0 0

run_threaded:
	java -cp bin/ ThreadedSimulation 0 0 0

clean:
	rm -f ${BINDIR}/*.class

docs:
	javadoc  -classpath ${BINDIR} -d ${DOCDIR} ${SRCDIR}/*.java

cleandocs:
	rm -rf ${DOCDIR}/*


