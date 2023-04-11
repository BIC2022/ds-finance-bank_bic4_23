Projekt: Trading Service
Projektbeschreibung
Das Ziel des Projekts ist der Entwurf einer Architektur für ein verteiltes System und die Einarbeitung in Middleware-Technologien für verteilte Systeme basierend auf der Java Enterprise Edition.
Zu diesem Zweck werden Sie eine einfache Bankanwendung implementieren, die ein TradingService zum Kauf und Verkauf von Aktien für Kunden aufruft.
Weiters werden Sie Client-Anwendungen implementieren, die Mitarbeitern bzw. Kunden zur Verfügung gestellt werden, um Online-Transaktionen durchzuführen.

Anforderungen
Das Projekt teilt sich in eine Server- und Client-Implementierung auf. Für den Bankserver (die Bank) ergeben sich folgende Aufgaben:

Die Bank ermöglicht es Kunden, Anteile von verfügbaren Aktien zu suchen, kaufen und verkaufen.
Die Bank verwaltet Depots für Kunden und speichert für jeden Kunden mit, wieviele Anteile für welche Aktien sich in seinem Depot befinden.
Um Aktien für einen Kunden zu kaufen oder zu verkaufen, verwendet die Bank ein Web Service der Börse.
Die WSDL dazu ist unter folgendem Link zu finden: https://edu.dedisys.org/ds-finance/ws/TradingService?wsdl.
Zwecks Überbrückung der Zeit bis zur WS-Einheit gibt es auch eine Javadoc-Beschreibung der Schnittstelle.
Der Aufruf des Web Services erfordert eine Authentifizierung. Die Authentifizierungsdaten erhalten Sie bei der Projekteinweisung.
Die Finanzdaten ab 24. November 2017 werden von IEX zur kostenfrei zur Verfügung gestellt und dürfen nur im Rahmen dieser Lehrveranstaltung verwendet werden.
Die IEX-Bedingungen zur Datennutzung finden sich unter https://iextrading.com/api-exhibit-a.
Das investierbare Volumen an der Börse ist pro Bank initial auf eine Milliarde Dollar beschränkt.
Der Kauf bzw. Verkauf von Aktien verringert bzw. erhöht diesen Wert entsprechend.
Die Bank führt Buch über diese Summe und passt sie nach an der Börse getätigten Käufen bzw. Verkäufen entsprechend an.
Sie können die Investment-Performance (aktuelles Gesamtvolumen) Ihrer Bank im Vergleich zu den Banken der anderen Gruppen auf folgender Seite einsehen: https://edu.dedisys.org/ds-finance/
Dieser Wert beginnt sich zu ändern, sobald Sie mit Ihrer Bank Aktien gekauft haben.
Die Aktualisierung erfolgt in der Nacht nach Werktagen. Wenn Sie gut investiert haben, steigt das Gesamtvolumen (Gewinn) – oder fällt, wenn Ihre Aktien Verlust machen.
Der Bankserver bietet remote Schnittstellen für Clients an, mit denen Kunden bzw. Mitarbeiter Transaktionen online durchführen können.
Mittels Client für Mitarbeiter können Mitarbeiter der Bank zumindest folgende Aktionen durchführen:

Anlegen von Kunden, wobei für einen Kunden mindestens Vor- und Nachname, Adresse und eine Kundennummer vergeben werden.
Suchen nach Kunden mittels Kundennummer oder Name des Kunden. Gehen Sie dabei davon aus, dass Kunden nicht immer ihre Kundennummer wissen.
Suchen nach verfügbaren Aktien.
Kaufen von Aktien für einen Kunden.
Verkaufen von Aktien für einen Kunden.
Auflisten aller Aktienanteile im Depot eines Kunden inkl. aktuellem Wert pro Firma und Gesamtwert des Depots.
Abfrage des aktuell investierbaren Volumens der Bank an der Börse.