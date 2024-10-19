# DataIntensive
DataIntesive Project basedon Mapreduce using hadoop Cluster
# Project Title
Enhancing Asian Development Bank Procurement Insight through MapReduce and HDFS.
## Overview
Purchasing products is a crucial factor in determining the direction of regional development and sustainability in the context of global economic dynamics. This study explores the complex world of goods procurement in the Asia-Pacific region with a particular emphasis on businesses that have been given contracts by the Asian Development Bank (ADB).
We aim to explore the significant significance that everyday import and export activities have for supplying the daily needs of the region and generating significant economic growth by closely examining these transactions.
We make use of the scalability of the MapReduce architecture and the capabilities of Python to navigate the massive dataset that is inherent in this study. Python, known for its adaptability and simplicity of use, proves to be a powerful tool for creating reliable and effective MapReduce algorithms.
To effectively handle and process the enormous amount of data, this study makes use of the Hadoop Distributed File System (HDFS) on the Hadoop architecture. With Hadoop's parallel processing power and HDFS's Ease of Use distributed storage architecture, we can overcome the obstacles presented by the volume of procurement data.
Our goal is to extract valuable information on regional trade trends and economic interdependencies from the dataset by utilizing MapReduce on HDFS.
This research highlights the potential of Python-driven MapReduce on HDFS for in-depth analysis of large-scale datasets, while also illuminating the strategic role of the ADB in financing regional initiatives. As we begin this investigation, the combination of Python, MapReduce, and HDFS becomes critical to deciphering the intricacies of acquiring products and advancing our knowledge of the regional economic dynamics.
To concentrate on my decision to work on the Asian Development Bank's procurement data, which summarizes the technical and financial growth of the nation, 
I must mention that I am an international student, which inspired me to learn about importing and exporting goods and the bank's mission of transparency 
and financial accountability between suppliers and owners. By analysing the procurement data, 
every impoverished person will be able to produce their own goods and will continue to grow, increasing the wealth of every
small town and fostering the development of every individual's business.
## Prerequisites
Hadoop Cluster
Hadoop Distributed File System Setup
Java Version:JDK 21
## Getting Started
Step1:
Install Java
At the terminal (console) of the virtual machine, execute each of the following commands in
turn. Press Enter after typing each command. This will apply to all commands entered at the
terminal.
When prompted, type y to confirm that you wish to install the packages. Note that the
command sudo runs any commands following it with superuser privileges.
Create a new user and group to run Hadoop
Enter the command below to add a new user-group to your system. The group is called hadoop.
sudo groupadd hadoop
Next create a new user, adding it to the group we just created. The username is hduser.
sudo useradd -ghadoop hduser -m -s /bin/bash
The newly-created user will need to have a password set. The command below will set the
password for the user hduser. When prompted, enter the password hadoop and confirm
it. Please do not change this password, as it will help ensure that your use of the VM can be
easily supported during lab sessions.
sudo passwd hduser
Finally, the newly-created user will need to be able to avail of superuser privileges. Run the
following command to add the user to the group with those privileges.
sudo usermod -aG sudo hduser
2
Set up passphrase-less SSH
You will be setting up Hadoop to run in pseudo-distributed mode. This is where Hadoop
launches separate process to imitate running across multiple interconnected nodes. In a
distributed environment, Hadoop uses the secure shell protocol (SSH) to communicate with
and manage its nodes.
We first need to log in to the hduser account. Do this by typing the following command.
su - hduser
The next step is to create a SSH (passphrase-less) key pair for the hduser account.
ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
The newly generated keys are then copied to hduser's authorized keys using the command
below.
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
In order to ensure that everything went well, we test that passphrase-less SSH works using the
following command.
The first time you run this command you will be asked to accept the key fingerprint. Do so and
you should find yourself back at the prompt.
ssh localhost
At this point you should be logged in to the hduser account using SSH. You should now exit
from the SSH shell session using the command below.
exit
Note: If are prompted for a password when executing the ssh localhost command then SSH is
not configured correctly. You should revisit all steps in this section until SSH works as
expected.
Disable IPv6
Hadoop does not currently run over IPv6 networks, so we need to disable IPv6 using the
command below.
sudo nano /etc/sysctl.conf
This will open a text editor. If you are a Linux pro you can, of course, use your favorite editor
instead (e.g., vi).
Add the following entries at the end of the file and then save the file by pressing Ctrl and O
simultaneously.
3
cd ~
curl -O https://archive.apache.org/dist/hadoop/common/hadoop-3.2.2/hadoop3.2.2.tar.gz.sha512
shasum -a 512 ./hadoop-3.2.2.tar.gz
cd ~
curl -O https://archive.apache.org/dist/hadoop/common/hadoop-3.2.2/hadoop-3.2.2.tar.gz
Exit the editor by pressing Ctrl and X simultaneously.
To make these changes take immediate effect, run the following command:
sudo sysctl -p
Step2:
##Install Hadoop
Download Hadoop
We will use the Hadoop 3.2.2. To download the Hadoop archive, type the following commands
in the terminal and press Enter after each one.
If curl is not found on your system, you may need to install it with the command below:
sudo apt install curl
Verify the downloaded archive file
Checking the integrity of the downloaded archive is optional but highly recommended
. Do this by entering the following commands.
Compare the output of this command with the line beginning with SHA512= in the
file hadoop-3.2.2.tar.gz.sha512. If the two match, the integrity of the archive can be assured.
Note that you can open the sha512 file with any text editor.
Unpack the downloaded archive file
We will now change the working directory to where we want to install Hadoop by entering the
following into the terminal and pressing Enter. This is where we will install all downloaded
software.
cd /usr/local
net.ipv6.conf.all.disable_ipv6=1
net.ipv6.conf.default.disable_ipv6=1
net.ipv6.conf.lo.disable_ipv6=1
4
sudo tar xvfz ~/hadoop-3.2.2.tar.gz
rm ~/hadoop-3.2.2.tar.gz
Next we unpack the archive we downloaded earlier. Type the two lines below into the terminal,
pressing Enter after each as usual. The second line removes (deletes) the downloaded archive
file as it is no longer needed.
The command tar followed by x unpacks an archive, much in the same way as zip archives are
extracted on Windows or MacOS. The tilde ~ is an alias for the current user's home directory.
Set permissions and create a symbolic link
Create a symbolic link (a form of shortcut) to alias the
directory /usr/local/hadoop with /usr/local/hadoop-3.2.2. This allows us to potentially have
multiple versions of Hadoop installed and to switch between the by simply deleting and
recreating the symbolic link.
sudo ln -s hadoop-3.2.2 hadoop
Last of all, we run a command to ensure that all files in the hadoop directories are owned by
the user hduser and the group hadoop.
sudo chown -R hduser:hadoop hadoop*
The command chown changes the ownership of a file or directory. The asterisk after the word
hadoop means that it will operate on any file or directory starting with that word.
Configure Hadoop
First change to the directory where the Hadoop configuration files are stored. This can be done
either by issuing a change directory command relative to the current directory (/usr/local) as
shown below:
cd hadoop/etc/hadoop
or by entering the full path (an absolute path) as in the following example.
cd /usr/local/hadoop/etc/hadoop
Step3:
Perform Your Map Reduce code in java add tempalate to Your hdfs Directory Along with your two CSV ie FinalNationality.CSV And OriginOfGoods.CSV
>>Now Compile your java code using hdfs commands 
hadoop com.sun.tools.javac.Main *.java
>>After compiling to generate the jar file should be generated using below command
jar cf resultNew.jar OriginOfGoodsAnalysis*.class
>>As the Jar File Generates pass the input Data File’s and give the output file Directory 
hadoop jar resultNew.jar OriginOfGoodsAnalysis FILE/originnew.csv FILE/FinalNationalitynew.csv FinalOutput-sample14
>>after this the output file generates and gives the output file in output Directory and generates the part file
hdfs dfs -ls FinalOutput-sample14/
>>And the output is displayed in Part file
hdfs dfs -get FinalOutput-sample14/part-r-00000
>>As the Part File Generates the output is displayed on terminal,
>>Also Visualise the Output





