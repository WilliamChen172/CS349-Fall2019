default:
	javac -sourcepath src src/*.java -d ./out --module-path $(JAVA_FX_HOME)/lib --add-modules javafx.controls
run:
	java -cp out --module-path $(JAVA_FX_HOME)/lib --add-modules javafx.controls Main
clean:
	-@rm out/*.class
