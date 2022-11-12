
At time of writing, testing with NodeJS v16 on Debian 10


Install NodeNS packages:

    npm install

Build static HTML/CSS/JS bundle.  For a public facing web
server 'gothello.interactwith.us' this would be:

    REACT_APP_GOTHELLO_WS_API="wss://gothello.interactwith.us/api/v0" \
    REACT_APP_GOTHELLO_API="https://gothello.interactwith.us/api/v0" \
    npm run build


Copy the generated files to your web root web folder. For example:

    sudo /bin/cp -r build /var/www/html-gothello

While the default web root directory /var/www/html is owned by root,
for the files you have just installed, you might like to change the file
ownership to match the username that web server runs as.  For Apache2
on Debian, the service runs by default as 'www-data':


    sudo chown -R www-data /var/www/html-gothello
    
An example Apache2 config file for operation over HTTPS follows.
Below that, using 'certbot' the commands to run to add a new
domain into an existing Apache2 web server.


cat /etc/apache2/sites-enabled/gothello-le-ssl.conf
#=====
<IfModule mod_ssl.c>
<VirtualHost *:443>
        ServerName gothello.interactwith.us

        ServerAdmin webmaster@localhost
        DocumentRoot /var/www/html-gothello

        ErrorLog ${APACHE_LOG_DIR}/gothello-error.log
        CustomLog ${APACHE_LOG_DIR}/gothello-access.log combined

        ProxyPass /api http://localhost:8080/api
        ProxyPassReverse /api http://localhost:8080/api

        # Set up websockets to work through the proxy
        RewriteEngine On
        RewriteCond %{HTTP:Upgrade} =websocket             [NC]
        RewriteCond %{REQUEST_URI}  ^/api                  [NC]
        RewriteRule /(.*)           ws://localhost:8080/$1 [P,L]

Include /etc/letsencrypt/options-ssl-apache.conf
SSLCertificateFile /etc/letsencrypt/live/gothello.interactwith.us/fullchain.pem
SSLCertificateKeyFile /etc/letsencrypt/live/gothello.interactwith.us/privkey.pem
</VirtualHost>

</IfModule>
#=====

sudo certbot --apache --expand -d gothello.interactwith.us

sudo systemctl restart apache2
