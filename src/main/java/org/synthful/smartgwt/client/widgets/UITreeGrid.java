package org.synthful.smartgwt.client.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.synthful.smartgwt.client.ListGridInit;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.tree.TreeGrid;

public class UITreeGrid extends TreeGrid implements HasWidgets {
	
	public UITreeGrid() {
		super();
	}

	@UiConstructor
	public UITreeGrid(ListGridInit init) {
		super();
		init.init(this);
	}

	@Override
	public void add(Widget widget) {
		if (widget instanceof UITreeGridField){
			ListGridField[] fields = getFields();

			ListGridField[] newFields = new ListGridField[fields.length+1];

			for (int i = 0; i < fields.length; i++) {
				newFields[i] = fields[i];
			}
			newFields[fields.length] = ((UIListGridField)widget).getSmartObject();
			setFields(newFields);
		}
		else if (widget instanceof UIListGridFieldArray){
			ListGridField[] newFields = ((UIListGridFieldArray)widget).toArray();
			setFields(newFields);
		}
		else if (widget instanceof UIDataSource){
			DataSource datasrc = ((UIDataSource)widget).getSmartObject();
			setDataSource(datasrc);
		}
	}

	@Override
	public Iterator<Widget> iterator() {
		List<Widget> fields = new ArrayList<Widget>(super.getFields().length);
		for (ListGridField field : super.getFields()) {
			fields.add(new UIListGridField(field));
		}
		return fields.iterator();
	}

	@Override
	public boolean remove(Widget widget) {
		if (widget instanceof UITreeGridField){
			ListGridField[] fields = getFields();

			ListGridField[] newFields = new ListGridField[fields.length-1];

			boolean remove = false;
			for (int i = 0; i < fields.length; i++) {
				UIListGridField newField = (UIListGridField)widget;
				if (!fields[i].equals(newField.getSmartObject())){
					remove = true;
					newFields[i] = fields[i];
				}
			}
			if (remove){
				setFields(newFields);
			}
			return remove;
		}
		return false;
	}
}