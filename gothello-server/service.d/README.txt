
To install the Gothello Spring based web server as a service

    ./INSTALL-SERVICE.sh www-data

The INSTALL-SERViCE script checks a few things first, and if all is
well, goes ahead and creates the 'gothello.service' file (in this folder),
and then installs it.

The script finishes by printing out some extra details, such as how to
use the service with 'systemctl', including how to add it in to the
boot-up sequence.

