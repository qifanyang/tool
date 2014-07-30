package guava.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.google.common.collect.BiMap;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

public class GuavaOfenUseTest extends TestCase{
	
	public void testUse(){
		//不可变元素测试
		Set<String> set = new HashSet<String>(Arrays.asList(new String[]{"RED", "GREEN"})); 
		Set<String> unmodifiableSet = Collections.unmodifiableSet(set);
		ImmutableSet<String> immutableSet = ImmutableSet.copyOf(set);
		
//		System.out.println(unmodifiableSet.add("tt"));
		
		set.add("tt");//修改了原始set,Collections.unmodifiableSet不可更改的set也变了
		System.out.println(unmodifiableSet);
		System.out.println(immutableSet);//修改了原来的set这里不受影响
		
		
		List<String> wordList = new ArrayList<String>();
		wordList.add("one");
		wordList.add("two");
		wordList.add("two");
		wordList.add("three");
		//Multiset: 把重复的元素放入集合
		HashMultiset<String> create = HashMultiset.create();
//		create.addAll(wordList);
		create.add("one");
		create.add("two");
		create.add("two");
		System.out.println("HashMultiset count :" + create.count("two"));
		
//		HashMultiset: 元素存放于 HashMap
//		LinkedHashMultiset: 元素存放于 LinkedHashMap，即元素的排列顺序由第一次放入的顺序决定
//		TreeMultiset:元素被排序存放于TreeMap
//		EnumMultiset: 元素必须是 enum 类型
//		ImmutableMultiset: 不可修改的 Mutiset
		
		
		//Multimap: 在 Map 的 value 里面放多个元素
//		Muitimap 接口的主要实现类有：
//		HashMultimap: key 放在 HashMap，而 value 放在 HashSet，即一个 key 对应的 value 不可重复
//		ArrayListMultimap: key 放在 HashMap，而 value 放在 ArrayList，即一个 key 对应的 value 有顺序可重复
//		LinkedHashMultimap: key 放在 LinkedHashMap，而 value 放在 LinkedHashSet，即一个 key 对应的 value 有顺序不可重复
//		TreeMultimap: key 放在 TreeMap，而 value 放在 TreeSet，即一个 key 对应的 value 有排列顺序
//		ImmutableMultimap: 不可修改的 Multimap
		
		HashMultimap<String, String> toupiao = HashMultimap.create();//value 不重复
		create.clear();
		create.addAll(toupiao.values());
		
		
		ArrayList<String> newArrayList = Lists.newArrayList();
		
		ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
				  "red",
				  "orange",
				  "yellow",
				  "green",
				  "blue",
				  "purple");
		
		ImmutableSet<String> copyOf = ImmutableSet.copyOf(COLOR_NAMES);
		
//		HashMultimap<String, String> multimap = HashMultimap.create();//value不重复
		
		LinkedListMultimap<String, String> multimap = LinkedListMultimap.create();//value可重复
		
//		ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
		
		multimap.put("k1", "k11");
		multimap.put("k1", "k11");
		multimap.put("k1", "k12");
		multimap.put("k2", "k21");
		
		System.out.println("k1 value size :" + multimap.get("k1").size());//value不重复
		System.out.println("keys  size :" + multimap.keySet().size());//多少个key元素,不重复
		System.out.println("values  size :" + multimap.size());//多少个value元素
		System.out.println("null values  size :" + multimap.get("ok").size());//
		
		//BiMap kv or vk inverse()
		BiMap<String, Integer> bimap = HashBiMap.create();//value重复要抛异常, key重复要覆盖
		bimap.put("name", 100);
		bimap.put("name", 1001);
		bimap.put("name2", 1002);
		String result = bimap.inverse().inverse().inverse().get(100);//
		System.out.println(result);
		
		
		//Table
		Table<Integer, Integer, Integer> weightedGraph = HashBasedTable.create();
		weightedGraph.put(1, 2, 4);
		weightedGraph.put(1, 3, 20);
		weightedGraph.put(2, 3, 5);

		weightedGraph.row(1); // returns a Map mapping v2 to 4, v3 to 20
		weightedGraph.column(2); // returns a Map mapping v1 to 20, v2 to 5
		
		//比较器
		ArrayList<Foo> fooList = Lists.newArrayList();
		Foo f1 = new Foo();
		Foo f2 = new Foo();
		f2.b = 5;
//		Foo f3 = new Foo();
//		f3.c = 87;
		fooList.add(f1);
		fooList.add(f2);
//		fooList.add(f3);
		
		Collections.sort(fooList, new Comparator<Foo>(){    
		    @Override 
		    public int compare(Foo f1, Foo f2) {
			    return ComparisonChain.start()  
			         .compare(f2.a, f1.a)  
			         .compare(f2.b, f1.b) 
			         .compare(f2.c, f1.c).result(); 
		      }});
		System.out.println(fooList.toString());
	}
	
	
	public static class Foo{
		public int a = 1;
		public int b = 2;
		public int c = 3;
		
		@Override
		public String toString() {
			return "["+a+":"+b+":"+b+"]";
		}
	}
	

}
