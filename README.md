# DockerVDI
The project aims to deploy graphical applications in Docker containers, over the network.
The architecture is simple : 
- One web and Docker repository server with a database 
- One client

## Requirements
Each part of the architecture can be a physical machine or a VM.
All the machine works on a Linux system : Ubuntu 12.04 LTS (x64).

## Configuration of the server
### Install Webserver Apache 2
The installation is simple, write in console: 
'''
sudo apt-get install apache2 apache2-utils php5 php5-dev php5-gd
'''
Verify the security file of the server: 
'''
sudo gedit /etc/apache2/conf.d/security
'''
Verify that these 3 lines are written like this: 
'''
ServerTokens Prod
Server Signature Off
TraceEnable Off
'''
Then create the virtualhost file for the server: 
'''
sudo gedit /etc/apache2/sites-available/dockerwebserver
'''
And fill it: 
'''
# virtualHost of dockerwebserver.fr
<VirtualHost *:80>
    ServerAdmin webmaster@dockerwebserver.fr      
    ServerName  dockerwebserver.fr           
    ServerAlias www.dockerwebserver.fr dockerwebserver.com 
    
    # Les documents du site (Souvent /var/www ou /home/monsite)
    DocumentRoot /var/www
    
    # Les options du site (comme dans un .htaccess)
    <Directory /var/www/>
        # On autorise tous le monde a voir le site
        Order allow,deny
        allow from all
    </Directory>
    
    # Les logs (historiques des IPs et des fichiers envoyés)
    ErrorLog /var/log/apache2/dockerwebserver.fr-error_log      # Erreurs
    TransferLog /var/log/apache2/dockerwebserver.fr-access_log  # Acces
</VirtualHost>
'''
Restart the web server:
'''
sudo /etc/init.d/apache2 restart 
'''
If everything is okay you should get an "It works" when you typing localhost in your webbrowser.
You can now copy the "images" folder provided by the project on the path /var/www/ of your server.

### Install Database MySQL
To install MySQL database just write in console: 
'''
sudo apt-get install mysql-server php5-mysql
'''
That's it ! If you need a graphical tool to manage MySQL, I recommend PhpMyAdmin. 
You can now import the test database, provided by the project:
'''
mysql -u root -p -h localhost test < test.sql
'''

### Install Docker
Due to a bug in LXC, Docker works best on the 3.8 kernel. Precise comes with a 3.2 kernel, so we need to upgrade it. The kernel you'll install when following these steps comes with AUFS built in.
'''
# install the backported kernel
sudo apt-get update
sudo apt-get install linux-image-generic-lts-raring linux-headers-generic-lts-raring

# install the backported kernel and xorg if using Unity/Xorg
sudo apt-get install --install-recommends linux-generic-lts-raring xserver-xorg-lts-raring libgl1-mesa-glx-lts-raring

# reboot
sudo reboot
'''
Now we can install Docker:
'''
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 36A1D7869245C8950F966E92D8576A8BA88D21E9
sudo sh -c "echo deb https://get.docker.com/ubuntu docker main > /etc/apt/sources.list.d/docker.list"
sudo apt-get update
sudo apt-get install lxc-docker
'''
Because the docker daemon always run as root user, it's convenient to add your username to docker group. 
This aim to provide access to docker command as non-root user.
'''
# Add the docker group if it doesn't already exist.
sudo groupadd docker

# Add the connected user "${USER}" to the docker group.
# Change the user name to match your preferred user.
sudo gpasswd -a ${USER} docker
'''
You have to logout and log back in again for this to take effect.

### Install and launch the docker repository
Now we have Docker on our server we can pull the official image of the docker repository:
'''
docker pull registry
'''
Once the image is fully download on the server, create a folder a the root of the system:
'''
sudo mkdir ./registry
sudo chmod 777 ./registry
'''
Now you can launch the repository like so:
'''
docker run -d -p 5000:5000 -v /registry:/tmp/registry:rw registry
'''

## Configuration of the client
### Install Docker 
Just follow instructions before.

### Install Java
The client is written in java so we need the jre to execute the jar file:
'''
sudo apt-get install openjdk-7-jre
'''

### Configuration of the environment
Some folders must be created for the java client:
'''
sudo mkdir /etc/.dockerimg/

sudo chmod 777 /etc/.dockerimg/

sudo mkdir /etc/.dockerimg/logo/ /etc/.dockerimg/scripts/ /etc/.dockerimg/appli/ /etc/.dockerimg/copyfiles/

sudo touch /etc/.dockerimg/scripts/savesession.sh /etc/.dockerimg/scripts/copyfolder.sh

sudo chmod 777 /etc/.dockerimg/scripts/ /etc/.dockerimg/appli/ /etc/.dockerimg/logo/ /etc/.dockerimg/copyfiles/ /etc/.dockerimg/scripts/savesession.sh /etc/.dockerimg/scripts/copyfolder.sh
'''

### Use the client
Download the jar file provided by the project and launch it. 

 


