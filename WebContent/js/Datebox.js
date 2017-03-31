zk.afterLoad("zul.db", function () {
            // Datebox Calendar Renderer
            var _Cwgt = {};
            zk.override(zul.db.CalendarPop.prototype, _Cwgt, {
                // switch the view after redraw or open as needed
                redraw: function (out) {
                    _Cwgt.redraw.apply(this, arguments); //call the original method
                    this._customChangeView ();
                },
                open: function (silent) {
                    _Cwgt.open.apply(this, arguments); //call the original method
                    this._customChangeView ();
                },
                _customChangeView: function () {
                    // cannot show month/day
                    if (jq(this.parent.$n()).hasClass('datebox-year-only')) {
                        var view = this._view;
                        // switch to year view as needed
                        if (view == 'month' || view == 'day')
                            this._setView("year");
                    } else if (jq(this.parent.$n()).hasClass('datebox-month-only')) {
                        // cannot show day view
                        // switch to month view as needed
                        if (this._view == 'day')
                            this._setView("month");
                    }
                },
                // customize the chooseDate function
                _chooseDate: function (target, val) {
                    var view = this._view;
                    if (jq(this.parent.$n()).hasClass('datebox-month-only')
                        || jq(this.parent.$n()).hasClass('datebox-year-only')) {
                        // do customize chooseDate if the parent (datebox)
                        // has specific class
                        var date = this.getTime(),
                            year = (view == 'decade' || view == 'year')? val : date.getFullYear(),
                            month = view == 'month'? val : 0,
                            date = 1;

                        // set date value
                        this._setTime(year, month, date);
                        if (view == 'decade') {
                            // switch to year view if at decade view
                            this._setView("year");
                        } else if (jq(this.parent.$n()).hasClass('datebox-month-only')
                            && view == 'year') {
                            // switch to month view if at year view and the month view is allowed
                            this._setView("month");
                        } else if (jq(this.parent.$n()).hasClass('datebox-month-only') && view == 'month'
                            || jq(this.parent.$n()).hasClass('datebox-year-only') && view == 'year') {
                            // close calendar and update value if already at the smallest allowed view
                            this.close();
                            this.parent.updateChange_();
                        }
                    } else {
                        _Cwgt._chooseDate.apply(this, arguments); //call the original method
                    }
                }
            });
        });