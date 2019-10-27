# Collectios - Immutable Collections

[![Build Status](https://travis-ci.org/elvinmahmudov/collectios.svg?branch=master)](https://travis-ci.org/elvinmahmudov/collectios)

### Getting Started
Collectios provide the following collection types:<br/>
CollectiosList - analogous to ArrayList<br/>
CollectioMap - analogous to HashMap<br/>
CollectiosSet - analogous to HashSet<br/>

### Installing
Step 1: Add to maven dependencies

```
        <dependency>
            <groupId>com.github.elvinmahmudov</groupId>
            <artifactId>collectios</artifactId>
            <version>1.1</version>
        </dependency>
```

Step 2: Start using Collectios

```
public class Main {

    public static void main(String[] args) {
        CList<String> list = CollectiosList.empty(); 
        list = list.add("first");

        System.out.println(list);
        System.out.println(list.add("second");
        System.out.println(list);
    }
}
```
The output will be:
```
[first]
[first, second]
[first]
```
