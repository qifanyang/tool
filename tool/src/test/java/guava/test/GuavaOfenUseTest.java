package guava.test;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;

import junit.framework.TestCase;

public class GuavaOfenUseTest extends TestCase{
	
	public void testUse(){
		List<String> wordList = new ArrayList<String>();
		wordList.add("one");
		wordList.add("two");
		wordList.add("two");
		wordList.add("three");
		//Multiset: 把重复的元素放入集合
		HashMultiset<String> create = HashMultiset.create();
		create.addAll(wordList);
		System.out.println(create.count("two"));
		
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
		
	}

}
