package info.duhovniy.tutorial.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Fragment {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "content_type")
	private String type;

	@Column(name = "title")
	private String title;

	@Column(name = "preview_description")
	private String previewDescription;

	@Column(name = "detail_description")
	private String detailDescription;

	@Column(name = "image")
	private String image;

	public Fragment() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreviewDescription() {
		return previewDescription;
	}

	public void setPreviewDescription(String previewDescription) {
		this.previewDescription = previewDescription;
	}

	public String getDetailDescription() {
		return detailDescription;
	}

	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
