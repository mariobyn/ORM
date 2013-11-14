package ObjectClasses;


import Annotations.Injection;

public class Job {

    private String name;

    private String location;

    @Injection
    Location loc;
}
