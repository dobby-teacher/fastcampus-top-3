package com.fastcampus.demogrpcclient;

import bookstore.BookServiceGrpc;
import bookstore.Bookstore;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookGrpcClient {
    @GrpcClient("local-grpc-server")
    private BookServiceGrpc.BookServiceBlockingStub bookServiceBlockingStub;

    public Bookstore.Book findById(final long bookId) {
        return this.bookServiceBlockingStub.getBookDetails(
                Bookstore.GetBookDetailsRequest
                        .newBuilder()
                        .setBookId(bookId)
                        .build()
        );
    }

    public List<Bookstore.Book> findAll() {
        Iterator<Bookstore.Book> bookIterator = this.bookServiceBlockingStub.listBooks(
                Bookstore.ListBooksRequest
                        .newBuilder()
                        .build()
        );

        List<Bookstore.Book> bookList = new ArrayList<>();
        while(bookIterator.hasNext()) {
            bookList.add(bookIterator.next());
        }

        return bookList;
    }
}
