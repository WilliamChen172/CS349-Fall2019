default:
	javac -sourcepath src src/*.java -d .
	jar cmf src/manifest.mf out/a2.jar *.class
	rm *.class

run: default
	java -jar out/a2.jar

clean:
	-@rm out/src/*.class
	-@rm out/*.jar
