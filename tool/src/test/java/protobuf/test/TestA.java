package protobuf.test;

import protobuf.test.SearchRequest.SearchRequestProto;
import protobuf.test.SearchRequest.SearchRequestProto.Builder;
import junit.framework.TestCase;

public class TestA extends TestCase{
	
	public void testO(){
		Builder srbuilder = SearchRequest.SearchRequestProto.newBuilder();
		
		srbuilder.setPageNumber(2)
				 .setResultPerPage(3)
				 .setQuery("sdfsdf");
		
		SearchRequestProto srproto = srbuilder.build();
		byte[] bytes = srproto.toByteArray();
		
		
		//
		
	}

}
