package ht.http;

import ht.http.handlers.IndexHandler;

public class HttpServerMain {
	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.register("/index", new IndexHandler());
		server.start(9091);
	}

}
