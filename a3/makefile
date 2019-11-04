default:
	javac -sourcepath src src/*.java -d ./out --module-path $(JAVA_FX_HOME)/lib --add-modules javafx.controls
	cp src/assets/*.png out
run:
	java -cp out --module-path $(JAVA_FX_HOME)/lib --add-modules javafx.controls Main
clean:
	-@rm out/*.class
	-@rm out/*.png
