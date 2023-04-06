package HWork_2;

import java.util.Iterator;

public class task1 {
    
    public static void main(String[] args) {
        // Создаем и формируем список контактов компании - contactCompany
        SingleLinkList<Contact> contactCompany = new SingleLinkList<>();
        contactCompany.addToEnd(new Contact(6, "Петрова Инна Васильева", "23-45-32"));
        contactCompany.addToEnd(new Contact(18, "Ватрушкин Олег Петрович", "23-45-33"));
        contactCompany.addToEnd(new Contact(24, "Соколова Ольга Олеговна", "23-45-34"));
        contactCompany.addToEnd(new Contact(56, "Забывайло Иван Петрович", "23-45-35"));
        contactCompany.addToEnd(new Contact(76, "Купальникова Марина Дмитровна", "23-45-36"));
        // Выводим список contactCompany
        System.out.println("Начальный список: ");
        for(Contact contact: contactCompany) {
            System.out.println(contact);
        }
       
        contactCompany.reverse();
        
        System.out.println("Результат после разворота списка: ");
        
        // Выводим список contactCompany после разворота
        for(Contact contact: contactCompany) {
            System.out.println(contact);
        }
    }

    // Создаем и описываем класс Contact
    static class Contact {
        int id;
        String name;
        String phone;
        public Contact(int id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }
        @Override
        public String toString() {
            return "Contact{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

    public static class SingleLinkList<T> implements Iterable<T> {

        ListItem<T> head;
        ListItem<T> tail;

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                ListItem<T> current = head;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public T next() {
                    T data = current.data;
                    current = current.next;
                    return data;
                }
            };
        }

        private static class ListItem<T> {
            T data;
            ListItem<T> next;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public void addToEnd(T item) {
            ListItem<T> newItem = new ListItem<>();
            newItem.data = item;
            if (isEmpty()) {
                head = newItem;
                tail = newItem;
            } else {
                tail.next = newItem;
                tail = newItem;
            }
        }
        // Процедура разворота списка
        public void reverse() {
            if (!isEmpty() && head.next != null) {
                tail = head;
                ListItem<T> current = head.next;
                head.next = null;
                while (current != null) {
                    ListItem<T> next = current.next;
                    current.next = head;
                    head = current;
                    current = next;
                }
            }
        }
    }
}
