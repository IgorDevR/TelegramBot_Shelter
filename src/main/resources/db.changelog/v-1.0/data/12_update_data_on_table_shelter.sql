UPDATE shelter
    SET
        name = 'Городская Общественная Организация «Защити Собак» ',
        address = 'г. Астана, проспект Кабанбай батыр, 31',
        phone = '+7(7172) 21-91-91',
        working_time = 'ПН-ВС с 08:00 – 17:00 ',
        about_shelter = 'Городская Общественная Организация «Защити Собак» - это приют для бездомных собак в г. Астана. ' ||
                        'В приюте находятся брошенные и  потерянные или травмированные животные, которые ждут своего ' ||
                        'хозяина. В нем живет почти 2500 собак. Большие и маленькие, пушистые и гладкие, веселые и ' ||
                        'задумчивые - и на всех одна большая мечта - встретить своего Человека и найти Дом. ' ||
                        'Приют был создан в марте 2010 года. Ситуация с бездомными животными в городе критическая, ' ||
                        'существующие приюты переполнены и не в силах принимать новых животных.  ' ||
                        'С каждым годом приют расширяется, однако не может вместить всех бездомных животных города.' ||
                        'По приблизительным оценкам в городе несколько десятков тысяч бездомных собак. После лечения,' ||
                        ' стерилизации и социализации принимаются меры (через средства массовой информации, при участии ' ||
                        'волонтеров) по поиску хозяина для животного. Если отдать животное не удается, то в приюте ' ||
                        'оно остается пожизненно.' ||
                        'Была проделана огромная работа по благоустройству приюта (Поставлен бетонный забор, проведено' ||
                        ' отопление и вода в дом, построены дополнительные вольеры с выгулами для собак, обустроена ' ||
                        'комната для кошек).' ||
                        'Наш приют-это благотворительная некоммерческая организация, спонсоров и финансирования у нас ' ||
                        'нет, а потому мы полностью зависим от пожертвований граждан. Нашим питомцам нужна ваша помощь! ' ||
                        'Вы можете сделать их жизнь лучше. Приюту всегда требуются корма, поводки и ошейники, лекарства,' ||
                        ' материальная и информационная поддержка. А еще нашим собакам очень нужна ласка и общение ' ||
                        'с человеком, поэтому мы всегда рады новым волонтерам.',
        shelter_safety_equipment = 'Находясь на территории приюта, пожалуйста, соблюдайте наши правила и технику ' ||
                                   'безопасности!' ||
                                   'Запрещается:' ||
                                   '•	Самостоятельно открывать выгулы и вольеры без разрешения работника приюта.' ||
                                   '•	Кормить животных. Этим Вы можете спровоцировать драку. Угощения разрешены ' ||
                                   'только постоянным опекунам и волонтерам, во время прогулок с животными на поводке.' ||
                                   '•	Оставлять после себя мусор на территории приюта и прилегающей территории.' ||
                                   '•	Подходить близко к вольерам и гладить собак через сетку на выгулах. ' ||
                                   'Животные могут быть агрессивны!' ||
                                   '•	Кричать, размахивать руками, бегать между будками или вольерами, пугать и ' ||
                                   'дразнить животных.' ||
                                   '•	Посещение приюта для детей дошкольного и младшего школьного возраста без ' ||
                                   'сопровождения взрослых.' ||
                                   '•	Нахождение на территории приюта детей среднего и старшего школьного возраста ' ||
                                   'без  сопровождения взрослых или письменной справки-разрешения от родителей или ' ||
                                   'законных представителей.' ||
                                   '•	Самостоятельно заходить в кошатник без разрешения сотрудников приюта.' ||
                                   '•	Подходить к лошади без разрешения работника приюта. Угощать лошадь можно ' ||
                                   'только в присутствие работника приюта.' ||
                                   '•	Посещение приюта в состоянии алкогольного, наркотического опьянения.' ||
                                   'Если Вы являетесь постоянным волонтером приюта, опекуном или Вам разрешили прогулку ' ||
                                   'с животным:' ||
                                   'Пожалуйста, соблюдайте очередь - погулять хотят все!  Уточните, когда Вы можете ' ||
                                   'выйти и сколько по времени будет длиться прогулка. Поводок и ошейник надевайте в ' ||
                                   'вольере и снимайте их только после того, как зайдете в вольер и закроете за собой ' ||
                                   'дверь. Гуляйте только в специально отведенном для этого месте.' ||
                                   '' ||
                                   'Во время прогулки запрещается:' ||
                                   '•	Выводить животное без разрешение сотрудника приюта.' ||
                                   '•	Покидать территорию приюта.' ||
                                   '•	Наматывать поводок на руку. Обязательно одной рукой держаться за петлю поводка.' ||
                                   '•	Допускать близкий контакт между собаками во время выгула во избежание драк.' ||
                                   '•	Кормить собак костями, пищевыми отходами и сладостями.' ||
                                   '•	Отпускать животных с поводка.' ||
                                   '•	Разбрасывать поводки, игрушки, чесалки и пр.' ||
                                   '' ||
                                   'Дорогие друзья! Все наши "нельзя"  и "запрещено"  - это не наша прихоть. ' ||
                                   'Не соблюдение техники безопасности может привести к трагическим последствиям. ' ||
                                   'Вы можете пострадать сами и покалечить животных. Давайте уважать друг друга! ' ||
                                   'Посещение приюта должно приносить радость Вам и нашим четвероногим друзьям. ' ||
                                   'Очень надеемся на Ваше понимание!'
WHERE shelter.id = 1;