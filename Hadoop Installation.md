sudo apt-get update

sudo apt-get install default-jdk
java --version

sudo addgroup hadoop
sudo adduser --ingroup hadoop hduser
//password 1234

sudo apt-get install ssh

sudo adduser hduser sudo

su hduser

ssh-keygen -t rsa -P ""

cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys

cd /

sudo mkdir usr/local/hadoop

wget https://downloads.apache.org/hadoop/common/hadoop-3.3.0/hadoop-3.3.0.tar.gz

sudo cp -r hadoop-3.3.0.tar.gz /usr/local/hadoop/

sudo chown -R hduser:hadoop /usr/local/hadoop

sudo nano ~/.bashrc

//Add the following content at the end of the bashrc file

#HADOOP VARIABLES START
export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
export HADOOP_INSTALL=/usr/local/hadoop
export PATH=$PATH:$HADOOP_INSTALL/bin
export PATH=$PATH:/usr/local/hadoop/bin/
export PATH=$PATH:$HADOOP_INSTALL/sbin
export HADOOP_MAPRED_HOME=$HADOOP_INSTALL
export HADOOP_COMMON_HOME=$HADOOP_INSTALL
export HADOOP_HDFS_HOME=$HADOOP_INSTALL
export YARN_HOME=$HADOOP_INSTALL
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_INSTALL/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_INSTALL/lib"
#HADOOP VARIABLES END

tar xzf hadoop-3.3.0.tar.gz
sudo mv hadoop-3.3.0/* /usr/local/hadoop/
sudo nano /usr/local/hadoop/etc/hadoop/hadoop-env.sh
//Add the following content at the end
export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64

sudo mkdir -p /app/hadoop/tmp
sudo chown hduser:hadoop /app/hadoop/tmp

sudo gedit /usr/local/hadoop/etc/hadoop/core-site.xml

//Add the following content at the end

<configuration>
 <property>
  <name>hadoop.tmp.dir</name>
  <value>/app/hadoop/tmp</value>
  <description>A base for other temporary directories.</description>
 </property>

 <property>
  <name>fs.default.name</name>
  <value>hdfs://localhost:54310</value>
  <description>The name of the default file system.  A URI whose
  scheme and authority determine the FileSystem implementation.  The
  uri's scheme determines the config property (fs.SCHEME.impl) naming
  the FileSystem implementation class.  The uri's authority is used to
  determine the host, port, etc. for a filesystem.</description>
 </property>
</configuration>

sudo gedit /usr/local/hadoop/etc/hadoop/mapred-site.xml

//Add the following content at the end

 <property>
  <name>mapred.job.tracker</name>
  <value>localhost:54311</value>
  <description>The host and port that the MapReduce job tracker runs
  at.  If "local", then jobs are run in-process as a single map
  and reduce task.
  </description>
 </property>

sudo mkdir -p /usr/local/hadoop_store/hdfs/namenode
sudo mkdir -p /usr/local/hadoop_store/hdfs/datanode
sudo chown -R hduser:hadoop /usr/local/hadoop_store
sudo gedit /usr/local/hadoop/etc/hadoop/hdfs-site.xml

Add the following content at the end

 <property>
  <name>dfs.replication</name>
  <value>1</value>
  <description>Default block replication.
  The actual number of replications can be specified when the file is created.
  The default is used if replication is not specified in create time.
  </description>
 </property>
 <property>
   <name>dfs.namenode.name.dir</name>
   <value>file:/usr/local/hadoop_store/hdfs/namenode</value>
 </property>
 <property>
   <name>dfs.datanode.data.dir</name>
   <value>file:/usr/local/hadoop_store/hdfs/datanode</value>
 </property>




hadoop namenode -format
start-dfs.sh
start-yarn.sh
jps