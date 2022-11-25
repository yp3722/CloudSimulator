# CloudSimulator - Yash Pharande

A tool for benchmarking various cloud datacenter implementation and workloads

# Features
* Allows for easy configuration of cloud datacenter architecture and modeling of workloads(no coding required from the user!!!)
* Defines different levels of access control in the form of cloud computing models ie SaaS, PaaS, IaaS etc
* Offers high degree of customization of various parameters (AllocationPolicies, Network Topologies , LoadBalancing , Vertical Scaling, Scheduling Policies etc)

# Prerequisites
This projects uses the following libraries:
- Java 17
- SBT
- CloudSimPlus (V7.3.0)
- Slf4j and Logback
- Scalatest Framework
- typesafeConfig



# How to Run
To run this project:

1. Compile and Start Server
```
sbt clean compile run
```
- Then select the appropriate simulation as per requirement

# Configuration options available to a CloudUser

Userspace configurations are found in Override.conf file

### SaaS Implementation
```

*User is allowed to only select from predefined Applications(Cloudlets) and their number

SaaS{
    #set cloudlet application type A or B
    cloudlet_type = A

    #Set Number of Cloudlets (1-40)
    cloudlet_count = 40
}

```
User cannot configure the software applicaiton parameters nor Virtual Machines and its parameters

### PaaS Implementation
```

*User has access to choose from list of pre-configured VMs 
*User can also simulate their own application behavior
 
PaaS{

    #user can select configured VM type (SMALL,MEDIUM,LARGE)
    vmType = LARGE

    #user can choose number of VM instances
    vmCount = 10


    #user application parameters
    application{

            #min resource utilization 
            minUtil = 0.5
            
            #max resource utilization
            maxUtil = 0.9

            # lenght(in Mips) of each application
            length = 15000
            
            #number of Processing elements required
            peCount = 2
            
            #total number of instances
            application_count = 5

            #Allows applications to be submitted with random delay to simulate real world scenario
            #TRUE or FALSE
            EnableSubmissionDelay=TRUE

        }
}

```
User cannot configure the VM attributes and are limited to chosing from pre-defined options from CloudProvider

### IaaS Implementation
```
 
*User can simulate their own application behavior
*User can describe custom VM by changing the params along with Cloudlet schedulling policies 

 
IaaS{
        #configure vm 
        VM{
                #Ram in MB (max = 32000)
                ram = 30000

                #Storage in MB (max = 2048000)
                storage = 250000

                #BW in Mb (max = 10000)
                bw = 10000

                #MIPS capacity of each host pe (max = 50000)
                mipsCapacity = 15000

                #Num of PEs (max 16)
                peCount = 2

                #Num of Vms
                vmCount = 2

                #Scheduler ( FAIR, SPACESHARED, TIMESHARED)
                scheduler=TIMESHARED

        }
        
        #describe application behaviour parameter
        application{

                    minUtil = 0.7

                    maxUtil = 0.9

                    length = 1500000

                    peCount = 2

                    application_count = 2

                    #TRUE or FALSE
                    EnableSubmissionDelay=FALSE

                }

        #describe autoscaling behaviour
        autoScaling{

               #enables vertical autoscaling for cpu
               enabled = "TRUE"

               #set lower threshold 
               loweUtilThreshold = 0.3
                
               #set upper threshold 
               upperUtilThreshold = 0.7

           }

}

```
User dosenot have access to change VMAllocation, VMMigration and Scheduling parameters the cloud org retains control over it

# Configuration options available to a CloudOrg

Orgspace configurations are found in Default.conf file

## Datacenter Parameters

```
dataCenterConfig {

    #VMAllocationPolicy "BESTFIT"/"FIRSTFIT"/"ROUNDROBIN"/"SIMPLE"
    allocationPolicy = "SIMPLE"

    #Number of Hosts
    hostCount = 20

    #Scheduling Interval
    interval = 1

}
```

## Host Parameters

```
hostConfig {

    #Ram in MB (32 GB) per host
    ram = 32000
    ramCost = 0.002
    
    #Storage in MB (2 TB) per host
    storage = 2048000
    storageCost = 0.0001
    
    #Bandwidth in Mb (10 Gbps) per host
    bw = 10000

    # Bandwidth cost per Mb
    bwCost = 0.0005
    
    #MIPS capacity of each host Processor
    mipsCapacity = 50000

    #Num of Processors
    peCount = 16
    
    #Execution cost per seccond
    costPerSec = 0.001

    #power consumption parameters
    #Startup Delay in sec
    startupDelay = 5.0

    #Shutdown Delay  in secs
    shutdownDelay = 3.0

    #all units in Watts
    startupPower = 5.0
    shutdownPower = 3.0
    staticPower = 35.0
    maxPower = 50.0

    #VM Scheduling policy ("TIME" for timeshared or "SPACE" for spaceshared)
    vmScheduler = "SPACE"
}
```

### PaaS VM parameters

```
VMConfig {

    Small {
        #Ram in MB (4 GB)
        ram = 2048

        #Storage in MB (256 GB)
        storage = 250000

        #BW in Mb (100 Mbps)
        bw = 1000

        #MIPS capacity of each host
        mipsCapacity = 10000

        #Num of PEs
        peCount = 2
    }

    Med {
            #Ram in MB (8 GB)
            ram = 8192

            #Storage in MB (512 GB)
            storage = 500000

            #BW in Mb (100 Mbps)
            bw = 1000

            #MIPS capacity of each host
            mipsCapacity = 20000

            #Num of PEs
            peCount = 4
        }

   Large{
           #Ram in MB (16 GB)
           ram = 16000

           #Storage in MB (1 TB)
           storage = 1000000

           #BW in Mb (1 Gbps)
           bw = 5000

           #MIPS capacity of each host
           mipsCapacity = 20000

           #Num of PEs
           peCount = 8
       }

}
```

### SaaS Configuration Parameters

```
CloudletConfig{

    #utilization parameters of application A
    applicationA {
        
        #resource utilization params
        minUtil = 0.3
        maxUtil = 1.0
        
        #in Mips
        length = 10000

        #required no. of processors
        peCount = 1

    }

    #utilization parameters of application B
    applicationB{

        #resource utilization params
        minUtil = 0.5
        maxUtil = 0.9

        #in Mips
        length = 15000

        #required no. of processors
        peCount = 2

    }

}
```



# Output
## SaaS implementation
### Scheduling 40 instances of App A 
![img_6.png](img_6.png)

## PaaS implementation 

### VM Selected - SMALL
![img_2.png](img_2.png)
# vs
### VM Selected - LARGE
![img_3.png](img_3.png)

#### *Notice the tradeoff between Cost and Execution time*

## IaaS Implementation 

### Vertical CPU AutoScaling Enabled
![img_4.png](img_4.png)
# VS
![img_5.png](img_5.png)

#### *Notice even when 9 cpu cores were assigned ExecTime did not change indicating Bottleneck of other resourecs RAM, Bandwidth*

```
Users can change the various parameters and run their own simulation.
 However certain combinations will lead in simulation failing 
 For eg - trying to create vms which exceed the host resources that are available, 
 improper configuration of allocation policy or VmScheduler along with autoscaling etc
```

