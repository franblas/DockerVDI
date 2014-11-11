#Java runtime
sudo apt-get install openjdk-7-jre  

#Creation dossiers
sudo mkdir /etc/.dockerimg/
sudo chmod 777 /etc/.dockerimg/
sudo mkdir /etc/.dockerimg/logo/ /etc/.dockerimg/scripts/ /etc/.dockerimg/appli/ /etc/.dockerimg/copyfiles/
sudo touch /etc/.dockerimg/scripts/savesession.sh /etc/.dockerimg/scripts/copyfolder.sh
sudo chmod 777 /etc/.dockerimg/scripts/ /etc/.dockerimg/appli/ /etc/.dockerimg/logo/ /etc/.dockerimg/copyfiles/ /etc/.dockerimg/scripts/savesession.sh /etc/.dockerimg/scripts/copyfolder.sh


#Ajout utilisateur dans groupe docker
sudo gpasswd -a $USER docker

#Logout
gnome-session-quit --logout --no-prompt