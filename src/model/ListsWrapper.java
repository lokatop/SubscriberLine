package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Вспомогательный класс для обёртывания списка адресатов.
 * Используется для сохранения списка адресатов в XML.
 */
@XmlRootElement(name = "InfoModelList")
@XmlSeeAlso({InfoModel.class,ChooseModel.class})
public class ListsWrapper {


        private List infoModelList;

        @XmlElement(name = "InfoModel")
        public List getInfoModels() {
            return infoModelList;
        }

        public void setInfoModels(List infoModelList) {
            this.infoModelList = infoModelList;
        }


}
