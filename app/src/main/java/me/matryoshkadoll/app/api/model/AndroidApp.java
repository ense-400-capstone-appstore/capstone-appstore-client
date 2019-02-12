package me.matryoshkadoll.app.api.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AndroidApp {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }




public class CreatedAt {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("timezone_type")
    @Expose
    private Integer timezoneType;
    @SerializedName("timezone")
    @Expose
    private String timezone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(Integer timezoneType) {
        this.timezoneType = timezoneType;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}


public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("updated_at")
    @Expose
    private UpdatedAt updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public UpdatedAt getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(UpdatedAt updatedAt) {
        this.updatedAt = updatedAt;
    }

}


public class Links {

    @SerializedName("first")
    @Expose
    private String first;
    @SerializedName("last")
    @Expose
    private String last;
    @SerializedName("prev")
    @Expose
    private Object prev;
    @SerializedName("next")
    @Expose
    private Object next;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public Object getPrev() {
        return prev;
    }

    public void setPrev(Object prev) {
        this.prev = prev;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

}


public class Meta {

    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("from")
    @Expose
    private Integer from;
    @SerializedName("last_page")
    @Expose
    private Integer lastPage;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("per_page")
    @Expose
    private Integer perPage;
    @SerializedName("to")
    @Expose
    private Integer to;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}


public class UpdatedAt {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("timezone_type")
    @Expose
    private Integer timezoneType;
    @SerializedName("timezone")
    @Expose
    private String timezone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(Integer timezoneType) {
        this.timezoneType = timezoneType;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
}