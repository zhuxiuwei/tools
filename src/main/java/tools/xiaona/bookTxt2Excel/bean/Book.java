package tools.xiaona.bookTxt2Excel.bean;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private List<String> ISBN = new ArrayList<>();
    private List<String> 作品语种 = new ArrayList<>();
    private List<String> 题名 = new ArrayList<>();
    private List<String> 版本 = new ArrayList<>();
    private List<String> 出版信息 = new ArrayList<>();
    private List<String> 稽核项 = new ArrayList<>();
    private List<String> 一般注 = new ArrayList<>();
    private List<String> 论题主题 = new ArrayList<>();
    private List<String> 个人名称等同知识责任 = new ArrayList<>();
    private List<String> 丛书 = new ArrayList<>();
    private List<String> 目次备注 = new ArrayList<>();
    private List<String> 概要 = new ArrayList<>();
    private List<String> 个人主题 = new ArrayList<>();
    private List<String> 变异题名 = new ArrayList<>();
    private List<String> 题名主题 = new ArrayList<>();

    private List<Version> versions = new ArrayList<>();

    public List<String> get目次备注() {
        return 目次备注;
    }

    public void set目次备注(List<String> 目次备注) {
        this.目次备注 = 目次备注;
    }

    public List<String> get概要() {
        return 概要;
    }

    public void set概要(List<String> 概要) {
        this.概要 = 概要;
    }

    public List<String> get个人主题() {
        return 个人主题;
    }

    public void set个人主题(List<String> 个人主题) {
        this.个人主题 = 个人主题;
    }

    public List<String> get变异题名() {
        return 变异题名;
    }

    public void set变异题名(List<String> 变异题名) {
        this.变异题名 = 变异题名;
    }

    public List<String> get题名主题() {
        return 题名主题;
    }

    public void set题名主题(List<String> 题名主题) {
        this.题名主题 = 题名主题;
    }

    public List<String> getISBN() {
        return ISBN;
    }

    public void setISBN(List<String> ISBN) {
        this.ISBN = ISBN;
    }

    public List<String> get作品语种() {
        return 作品语种;
    }

    public void set作品语种(List<String> 作品语种) {
        this.作品语种 = 作品语种;
    }

    public List<String> get题名() {
        return 题名;
    }

    public void set题名(List<String> 题名) {
        this.题名 = 题名;
    }

    public List<String> get版本() {
        return 版本;
    }

    public void set版本(List<String> 版本) {
        this.版本 = 版本;
    }

    public List<String> get出版信息() {
        return 出版信息;
    }

    public void set出版信息(List<String> 出版信息) {
        this.出版信息 = 出版信息;
    }

    public List<String> get稽核项() {
        return 稽核项;
    }

    public void set稽核项(List<String> 稽核项) {
        this.稽核项 = 稽核项;
    }

    public List<String> get一般注() {
        return 一般注;
    }

    public void set一般注(List<String> 一般注) {
        this.一般注 = 一般注;
    }

    public List<String> get论题主题() {
        return 论题主题;
    }

    public void set论题主题(List<String> 论题主题) {
        this.论题主题 = 论题主题;
    }

    public List<String> get个人名称等同知识责任() {
        return 个人名称等同知识责任;
    }

    public void set个人名称等同知识责任(List<String> 个人名称等同知识责任) {
        this.个人名称等同知识责任 = 个人名称等同知识责任;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public List<String> get丛书() {
        return 丛书;
    }

    public void set丛书(List<String> 丛书) {
        this.丛书 = 丛书;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", 作品语种=" + 作品语种 +
                ", 题名=" + 题名 +
                ", 版本=" + 版本 +
                ", 出版信息=" + 出版信息 +
                ", 稽核项=" + 稽核项 +
                ", 一般注=" + 一般注 +
                ", 论题主题=" + 论题主题 +
                ", 个人名称等同知识责任=" + 个人名称等同知识责任 +
                ", 丛书=" + 丛书 +
                ", 目次备注=" + 目次备注 +
                ", 概要=" + 概要 +
                ", 个人主题=" + 个人主题 +
                ", 变异题名=" + 变异题名 +
                ", 题名主题=" + 题名主题 +
                ", versions=" + versions +
                '}';
    }
}
