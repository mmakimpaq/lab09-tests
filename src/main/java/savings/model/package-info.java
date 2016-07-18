@TypeDefs({
    @TypeDef(name = "percentage", defaultForType = Percentage.class, typeClass = PersistentPercentage.class),
    @TypeDef(name = "dateTime", defaultForType = DateTime.class, typeClass = PersistentDateTime.class),
    @TypeDef(name = "money", defaultForType = Money.class, typeClass = PersistentMoneyAmount.class, parameters = {
        @Parameter(name = "currencyCode", value = "EUR")
    })
})
package savings.model;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount;
import org.joda.money.Money;
import org.joda.time.DateTime;

import common.hibernate.PersistentPercentage;
import common.math.Percentage;