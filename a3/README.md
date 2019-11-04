William Chen  
20679103 w279chen  
openjdk version "13.0.1" 2019-10-15  
macOS 10.15 (MacBook Pro 2015)

Please replace $(JAVA_FX_HOME) with your javafx library
in the makefile

My enhancement feature is adding graphics texture to the game.
Because of assets loading when the game starts, it could be 
a little slow when you start the game.

Tapping R when running the game takes the user back
to the home screen and resets the game. The user will
have to start again. Tapping Q displays the high score
screen and the user has the option to restart the game
from there.
