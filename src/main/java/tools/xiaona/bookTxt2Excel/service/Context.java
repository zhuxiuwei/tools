package tools.xiaona.bookTxt2Excel.service;

import tools.xiaona.bookTxt2Excel.bean.Book;
import tools.xiaona.bookTxt2Excel.bean.Copy;
import tools.xiaona.bookTxt2Excel.bean.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理中的上下文信息
 */
public class Context {
    private List<Book> bookData = new ArrayList<>();  //最后解析的图书结果
    private Book currentBook;   //当前解析中的书
    private Version currentVersion;   //当前解析中的版本
    private Copy currentCopy;   //当前解析中的拷贝
    private CurrentHandlePart currentHandlePart = CurrentHandlePart.NONE;    //当前处理的部分。初始为NONE

    //上下文重置
    public void resetContext(){
        this.currentBook = null;
        this.currentVersion = null;
        this.currentCopy = null;
        this.currentHandlePart = CurrentHandlePart.NONE;
    }

    public void addNewBookToBookData(Book book){
        this.bookData.add(book);
    }

    /*getter/setter*/
    public CurrentHandlePart getCurrentHandlePart() {
        return currentHandlePart;
    }

    public void setCurrentHandlePart(CurrentHandlePart currentHandlePart) {
        this.currentHandlePart = currentHandlePart;
    }

    public List<Book> getBookData() {
        return bookData;
    }

    public void setBookData(List<Book> bookData) {
        this.bookData = bookData;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Version currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Copy getCurrentCopy() {
        return currentCopy;
    }

    public void setCurrentCopy(Copy currentCopy) {
        this.currentCopy = currentCopy;
    }
}
