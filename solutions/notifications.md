- NotificationsManager должен обработать ситуацию что пришло новое уведомление и иметь возможность
отправить сообщение в UI (любые слушатели его отобразили), 
логично что бы жизненный цикл его был Singleton (:data модуль)
- NotificationsUtil - или можно придумать другое название(NotificationsListener) должен
  иметь доступ к Intent(MainActivity), так как внутри у него нужно указывать Intent со ссылкой на Activity
  (пока оставить в:app модуле)
- AppFirebaseMessagingService - он поидее должен взаимодействовать с ProjectApplication
  (пока оставить в:app модуле)

Варианты реализации:
1. Создать модуль notification и туда поместить NotificationsManager + интерфейс,
   интерфейс NotificationsUtil, модель данных, сервис уведомлений. При получении уведомления
   из NotificationsManager дергать слушатель NotificationsListener. (Пробоавал,
   AppFirebaseMessagingService поидее должен быть на уровне app, так как должен
инжектить поля и взамодействововать ProjectApplication)
2. В данный момент все менеджеры и синглтоны находяться в data слое, пока логичнее его держать там
(NotificationsManager отвечает за обработку данных, все логично - оставил этот)
