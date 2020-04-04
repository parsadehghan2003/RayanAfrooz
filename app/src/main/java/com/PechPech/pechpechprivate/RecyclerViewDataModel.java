package com.PechPech.pechpechprivate;

/* renamed from: ir.rayanafrooz.app.RecyclerViewDataModel */
public class RecyclerViewDataModel {
    private boolean Selected;
    private String date;
    private String description;

    /* renamed from: id */
    private int id;
    private int isRead;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    RecyclerViewDataModel() {
    }

    /* access modifiers changed from: 0000 */
    public String getDate() {
        return this.date;
    }

    /* access modifiers changed from: 0000 */
    public void setDate(String date2) {
        this.date = date2;
    }

    public int getId() {
        return this.id;
    }

    public void setRead(int read) {
        this.isRead = read;
    }

    public int isRead() {
        return this.isRead;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* access modifiers changed from: 0000 */
    public String getDescription() {
        return this.description;
    }

    /* access modifiers changed from: 0000 */
    public void setDescription(String description2) {
        this.description = description2;
    }

    public boolean isSelected() {
        return this.Selected;
    }

    public void setSelected(boolean selected) {
        this.Selected = selected;
    }
}
