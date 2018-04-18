package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Вспомогательный класс для обёртывания списка адресатов.
 * Используется для сохранения списка адресатов в XML.
 */
@XmlRootElement(name = "List")
@XmlSeeAlso({InfoModel.class, ChooseModel.class, TheLastTable.class, TableViewTypeDef1.class})
public class ListWrapper {

    private List list;

    @XmlElement(name = "item")
    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
