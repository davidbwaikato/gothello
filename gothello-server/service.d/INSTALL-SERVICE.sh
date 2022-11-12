#!/bin/bash

full_progname="$PWD/${BASH_SOURCE#./}"
gothello_home=${full_progname%/*/*}

if [ -d "/etc/systemd/system/" ] ; then

    gothello_service_username=${1-www-data}
    echo ""
    
    echo "****"
    echo "* Generating gothello.service from gothello.service.in"
    echo "****"
    cat gothello.service.in \
	| sed "s%@GOTHELLO_HOME@%$gothello_home%g" \
	| sed "s%@GOTHELLO_SERVICE_USERNAME@%$gothello_service_username%g" \
	      > gothello.service
     
    echo "****"
    echo "* Copying gothello.service to /etc/systemd/system/"
    echo "****"
    sudo /bin/cp gothello.service /etc/systemd/system/.

    echo ""
    echo "----"
    echo "General info:"
    echo "  In the event of the service being updated, you will most likely need to run:"
    echo "    sudo systemctl daemon-reload"
    echo ""
    echo "  To enable this service to be run at boot-up time, run:"
    echo "    sudo systemctl enable gothello"
    echo "----"
    
else
    echo "Error: Failed to find '/etc/systemd/system'" >&2
    echo "This install script was developed on a Debian system." >&2
    echo "It looks like your Linux Distribution uses a different directory structure for services" >&2

    exit 1
fi  

