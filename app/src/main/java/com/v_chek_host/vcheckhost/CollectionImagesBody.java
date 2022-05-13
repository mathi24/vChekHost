package com.v_chek_host.vcheckhost;

public class CollectionImagesBody {

    private String user_id, record_count, starting_index;

    public CollectionImagesBody(String user_id, String record_count, String starting_index) {
        this.user_id = user_id;
        this.record_count = record_count;
        this.starting_index = starting_index;
    }

    public CollectionImagesBody() {
    }
}
