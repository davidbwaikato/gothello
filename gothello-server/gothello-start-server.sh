#!/bin/bash

echo ""
echo "Logging Gothello Server output to:"
echo "    gothello-server-$$.log"
echo ""

mvn spring-boot:run > gothello-server-$$.log 2>&1 &


