#!/bin/sh

SAKULI_PROJECT_HOME=~/git-files/sakuli
SAKULI_INST_FOLDER=$SAKULI_PROJECT_HOME/sakuli_app
SAKULI_VERSION=0.9.1-SNAPSHOT
export SAKULI_HOME=$SAKULI_INST_FOLDER/sakuli/sakuli-v$SAKULI_VERSION
SAHI_HOME=$SAKULI_PROJECT_HOME/sahi

SUITE=src/core/dev_stuff/suites/ubuntu

VNC_DISPLAY=1

PORT="590"$VNC_DISPLAY
notify-send -t 10 -u low -i $SAKULI_HOME/bin/resources/robotic1.png "Sakuli test '$SUITE' starting now on display $VNC_DISPLAY (localhost:$PORT)."
DISPLAY=:$VNC_DISPLAY

#vncserver should run!
#vncserver $DISPLAY -depth 24 -geometry 1024x768 &

$SAKULI_INST_FOLDER/sakuli/sakuli-v$SAKULI_VERSION/bin/sakuli.sh --run "$SAKULI_PROJECT_HOME/$SUITE" --sahi_home "$SAHI_HOME"

#killall Xvnc4
