package name.dickie.android.demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;


public class RenderVideoList implements Parcelable {
    public static final Creator<RenderVideoList> CREATOR = new Creator<RenderVideoList>() {
        @Override
        public RenderVideoList createFromParcel(Parcel source) {
            Log.e("wxd-demo", "create form source....");
            return new RenderVideoList(source);
        }

        @Override
        public RenderVideoList[] newArray(int size) {
            Log.e("wxd-demo", "create from newArray...");
            return new RenderVideoList[size];
        }
    };
    private String behavior;
    private int size;
    private String title;
    private String token;
    private List<VideoItem> videoItems;

    public RenderVideoList() {
    }

    protected RenderVideoList(Parcel in) {
        this.behavior = in.readString();
        this.size = in.readInt();
        this.title = in.readString();
        this.token = in.readString();
        this.videoItems = in.createTypedArrayList(VideoItem.CREATOR);
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<VideoItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.behavior);
        dest.writeInt(this.size);
        dest.writeString(this.title);
        dest.writeString(this.token);
        dest.writeTypedList(this.videoItems);
    }

    public static class VideoItem implements Parcelable {
        public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
            @Override
            public VideoItem createFromParcel(Parcel source) {
                return new VideoItem(source);
            }

            @Override
            public VideoItem[] newArray(int size) {
                return new VideoItem[size];
            }
        };
        private int numeration;
        private String title;
        private String titleSubtext1;
        private String url;
        private String videoItemId;

        public VideoItem() {
        }

        protected VideoItem(Parcel in) {
            this.numeration = in.readInt();
            this.title = in.readString();
            this.titleSubtext1 = in.readString();
            this.url = in.readString();
            this.videoItemId = in.readString();
        }

        public int getNumeration() {
            return numeration;
        }

        public void setNumeration(int numeration) {
            this.numeration = numeration;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleSubtext1() {
            return titleSubtext1;
        }

        public void setTitleSubtext1(String titleSubtext1) {
            this.titleSubtext1 = titleSubtext1;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideoItemId() {
            return videoItemId;
        }

        public void setVideoItemId(String videoItemId) {
            this.videoItemId = videoItemId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.numeration);
            dest.writeString(this.title);
            dest.writeString(this.titleSubtext1);
            dest.writeString(this.url);
            dest.writeString(this.videoItemId);
        }
    }
}