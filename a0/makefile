default:
	javac -sourcepath src src/Main.java -d out/src
	jar cmf src/manifest.mf out/Main.jar -C out/src Main.class

run: default
	java -jar out/Main.jar

clean:
	-@rm out/src/production/*.class	
	-@rm out/src/*.class
	-@rm out/*.jar