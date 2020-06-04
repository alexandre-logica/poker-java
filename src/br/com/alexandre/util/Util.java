package br.com.alexandre.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class Util {

	public static Map<Integer, Long> sortByValueInteger(Map<Integer, Long> hm) { 
        // Create a list from elements of HashMap 
        List<Map.Entry<Integer, Long> > list = new LinkedList<Map.Entry<Integer, Long> >(hm.entrySet()); 
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<Integer, Long> >() { 
            public int compare(Map.Entry<Integer, Long> o1,  
                               Map.Entry<Integer, Long> o2) 
            { 
            	int sComp = o2.getValue().compareTo(o1.getValue()); 
                if (sComp != 0) {
                    return sComp;
                 } 
                return o2.getKey().compareTo(o1.getKey());
            } 
        }); 
        // put data from sorted list to hashmap  
        Map<Integer, Long> temp = new LinkedHashMap<Integer, Long>(); 
        for (Map.Entry<Integer, Long> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }
	
	public static Map<String, Long> sortByValueString(Map<String, Long> hm) { 
        // Create a list from elements of HashMap 
        List<Map.Entry<String, Long> > list = new LinkedList<Map.Entry<String, Long> >(hm.entrySet()); 
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Long> >() { 
            public int compare(Map.Entry<String, Long> o1,  
                               Map.Entry<String, Long> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 
        // put data from sorted list to hashmap  
        Map<String, Long> temp = new LinkedHashMap<String, Long>(); 
        for (Map.Entry<String, Long> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    }
	
    public static List<Integer> orderedWithNoGap(SortedSet<Integer> list) {       
        Integer prev = null;
        int seq = 0;
        List<Integer> straight = new ArrayList<Integer>();
        for(Integer i : list) {
            if(prev != null && prev+1 == i) {
                if(seq == 0) {
                	seq = 2;
                	straight.add(prev);
                	straight.add(0, i);
                }else {
                	seq++;
                	straight.add(0, i);
                }
            }else {
            	seq = 0;
            	straight.clear();
            }
            if(seq == 5)
            	return straight;
            prev = i;
        }
        return straight;
    }
}
