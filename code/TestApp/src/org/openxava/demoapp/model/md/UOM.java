package org.openxava.demoapp.model.md;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;
import org.openxava.annotations.Tab;
import org.openxava.annotations.View;
import org.openxava.demoapp.etc._Link;
import org.openxava.demoapp.model.md.enums.MeasureCategory;
import org.openxava.demoapp.tracking.SimpleAccessTrackingListener;
import org.openxava.ex.model.base.BaseMasterDataModel;

/**
 * The unit of measure master data
 * @author root
 *
 */
@Entity
@EntityListeners(SimpleAccessTrackingListener.class)
@Table(name="MD_UOM")
@Tab(baseCondition = "enabled=true", properties="code, name, category, descr")
@View(name="V-UOM-code-name", members="code; name")
public class UOM extends BaseMasterDataModel{
	
	//BP: Save the first letter of Enum to database
	//BP: Use constant (_Link.*) to improve readability and maintainability
	@Type(type=_Link.EnumLetterType, parameters={
			@Parameter(name="letters", value=MeasureCategory.LETTERS),
			@Parameter(name="enumType", value=_Link.MeasureCatogory)
	})
	@Column(length=2) @Required
	private MeasureCategory category;

	public MeasureCategory getCategory() {
		return category;
	}

	public void setCategory(MeasureCategory category) {
		this.category = category;
	}
	
	//BP: Use @Transient to define the calculated field, and take no effect to object load and store
	//BP: Use @Transient and @Hidden to hide the calculated field
	@Transient @Hidden
	public String getDisplayName(){
		return this.getCode() + " - " + this.getName();
	}
}
